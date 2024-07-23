package au.gov.qld.ssq.kiteworks;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyConfig {

    @Value("${http_proxy:}")
    private String httpProxy;

    @Value("${https_proxy:}")
    private String httpsProxy;

    @Value("${non_proxy_hosts:}")
    private String nonProxyHosts;

    @PostConstruct
    public void setSystemProxy() {
        System.setProperty("java.net.useSystemProxies", "true");

        if (!httpProxy.isEmpty()) {
            String[] httpProxyParts = httpProxy.replace("http://", "").replace("https://", "").split(":");
            if (httpProxyParts.length == 2) {
                System.setProperty("http.proxyHost", httpProxyParts[0]);
                System.setProperty("http.proxyPort", httpProxyParts[1]);
            }
        }

        if (!httpsProxy.isEmpty()) {
            String[] httpsProxyParts = httpsProxy.replace("http://", "").replace("https://", "").split(":");
            if (httpsProxyParts.length == 2) {
                System.setProperty("https.proxyHost", httpsProxyParts[0]);
                System.setProperty("https.proxyPort", httpsProxyParts[1]);
            }
        }

        if (!nonProxyHosts.isEmpty()) {
            System.setProperty("http.nonProxyHosts", nonProxyHosts);
            System.setProperty("https.nonProxyHosts", nonProxyHosts);
        }
    }
}
