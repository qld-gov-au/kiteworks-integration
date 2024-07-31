import re

def parse_signature(signature):
    # Extract the method name and parameters from the signature
    method_pattern = re.compile(r'public\s+\w+<\w+>\s+(\w+)\((.*?)\) throws \w+ {')
    match = method_pattern.search(signature)
    method_name = match.group(1)
    params = match.group(2)

    # Split parameters into a list of tuples (type, name)
    param_list = []
    param_pattern = re.compile(r'(\w+<\w+>|List<\w+>|Map<\w+, \w+>|String|Boolean|Integer|File|LocalDate|int|boolean) (\w+)')
    for param in params.split(','):
        param = param.strip()
        match = param_pattern.match(param)
        param_list.append((match.group(1), match.group(2)))

    return method_name, param_list

def generate_builder_class(method_name, param_list):
    class_name = ''.join([word.capitalize() for word in re.findall(r'\w+', method_name)]) + "RequestBuilder"

    builder_class = f"public class {class_name} {{\n"

    # Declare private fields
    for param_type, param_name in param_list:
        builder_class += f"    private {param_type} {param_name};\n"

    builder_class += "\n"

    # Generate with methods
    for param_type, param_name in param_list:
        method = f"""
    public {class_name} with{param_name[0].upper() + param_name[1:]}({param_type} {param_name}) {{
        this.{param_name} = {param_name};
        return this;
    }}\n"""
        builder_class += method

    builder_class += "\n    // Add build method to return the built object\n"
    builder_class += f"    public {class_name} build() {{\n"
    builder_class += "        return this;\n"
    builder_class += "    }\n"

    builder_class += "\n    // Getter methods for each field (if needed)\n"
    for param_type, param_name in param_list:
        getter = f"""
    public {param_type} get{param_name[0].upper() + param_name[1:]}() {{
        return {param_name};
    }}\n"""
        builder_class += getter

    builder_class += "}\n"

    return builder_class

def generate_call_method(method_name, param_list):
    builder_class_name = ''.join([word.capitalize() for word in re.findall(r'\w+', method_name)]) + "RequestBuilder"

    call_method = f"public void call{method_name[0].upper() + method_name[1:]}() throws ApiException {{\n"
    call_method += f"    {builder_class_name} builder = new {builder_class_name}()\n"

    for param_type, param_name in param_list:
        call_method += f"        .with{param_name[0].upper() + param_name[1:]}(/* {param_name}Value */)\n"

    call_method += "        .build();\n\n"

    call_method += f"    {method_name}(\n"
    for param_type, param_name in param_list:
        call_method += f"        builder.get{param_name[0].upper() + param_name[1:]}(),\n"
    call_method = call_method.rstrip(",\n") + "\n    );\n"

    call_method += "}\n"

    return call_method

def main():
    signature = input("Enter the method signature: ")
    method_name, param_list = parse_signature(signature)

    builder_class = generate_builder_class(method_name, param_list)
    call_method = generate_call_method(method_name, param_list)

    print("Builder Class:\n")
    print(builder_class)

    print("\nCall Method:\n")
    print(call_method)

if __name__ == "__main__":
    main()
