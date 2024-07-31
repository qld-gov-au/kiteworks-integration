set -ex

echo GITHUB_TOKEN=$GITHUB_TOKEN
echo GITHUB_ORG=$GITHUB_ORG
echo GITHUB_REPO=$GITHUB_REPO
echo RELEASE_VERSION=$RELEASE_VERSION

echo "Listing all package versions for organization $GITHUB_ORG..."

# Get the list of packages
#packages_response=$(curl -s -H "Authorization: token $GITHUB_TOKEN" \
#  -H "Accept: application/vnd.github.v3+json" \
#  "https://api.github.com/orgs/$GITHUB_ORG/packages?package_type=maven&repository_id=$GITHUB_REPO_ID")

page=1
packages_response=""
# Fetch all pages of packages
while :; do
  response=$(curl -s -H "Authorization: token $GITHUB_TOKEN" \
                    -H "Accept: application/vnd.github.v3+json" \
                    "https://api.github.com/orgs/$GITHUB_ORG/packages?package_type=maven&repository_id=$GITHUB_REPO_ID&page=$page&per_page=100")

    # Initialize variables for package versions
    page=1
    versions_response=""

    # Fetch all pages of package versions
    while :; do
      response=$(curl -s -H "Authorization: token $GITHUB_TOKEN" \
                        -H "Accept: application/vnd.github.v3+json" \
                        "https://api.github.com/orgs/$GITHUB_ORG/packages/maven/$package/versions?page=$page&per_page=100")

      if [ -z "$response" ] || [ "$response" == "[]" ]; then
        break
      fi

      if [ -z "$versions_response" ]; then
        versions_response="$response"
      else
        versions_response=$(echo "$versions_response" | jq -s 'add' - "$response")
      fi

      page=$((page + 1))
    done

  if [ -z "$response" ] || [ "$response" == "[]" ]; then
    break
  fi

  if [ -z "$packages_response" ]; then
    packages_response="$response"
  else
    packages_response=$(echo "$packages_response" | jq -s 'add' - "$response")
  fi

  page=$((page + 1))
done

# Extract package names
package_names=$(echo "$packages_response" | jq -r '.[].name')


if [ -z "$package_names" ]; then
  echo "No packages found in repository $GITHUB_REPO"
  exit 0
fi

echo "Found packages: $package_names"

for package in $package_names; do
  echo "Processing package: $package"

  # Get the list of package versions
#  versions_response=$(curl -s -H "Authorization: token $GITHUB_TOKEN" \
#               -H "Accept: application/vnd.github.v3+json" \
#               "https://api.github.com/orgs/$GITHUB_ORG/packages/maven/$package/versions")

  page=1
  versions_response=""

  # Fetch all pages of package versions
  while :; do
    response=$(curl -s -H "Authorization: token $GITHUB_TOKEN" \
                      -H "Accept: application/vnd.github.v3+json" \
                      "https://api.github.com/orgs/$GITHUB_ORG/packages/maven/$package/versions?page=$page&per_page=100")

    if [ -z "$response" ] || [ "$response" == "[]" ]; then
      break
    fi

    if [ -z "$versions_response" ]; then
      versions_response="$response"
    else
      versions_response=$(echo "$versions_response" | jq -s 'add' - "$response")
    fi

    page=$((page + 1))
  done

  # Extract version IDs for the specified release version
  version_ids=$(echo "$versions_response" | jq -r --arg version "$RELEASE_VERSION" '.[] | select(.name == $version) | .id')

  if [ -z "$version_ids" ]; then
    echo "No versions found for $RELEASE_VERSION in package $package"
  else
    echo "Found versions for $RELEASE_VERSION in package $package: $version_ids"
    for version_id in $version_ids; do
      echo "Deleting version ID $version_id from package $package..."
      curl -X DELETE -H "Authorization: token $GITHUB_TOKEN" \
           -H "Accept: application/vnd.github.v3+json" \
           "https://api.github.com/orgs/$GITHUB_ORG/packages/maven/$package/versions/$version_id"
    done
  fi
done
