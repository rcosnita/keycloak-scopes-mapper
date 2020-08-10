package ro.rcosnita.keycloak.mappers;

import org.jboss.logging.Logger;
import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.AccessToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Provides a simple example which overrides the scope entry of the token.
 */
public class SampleMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper {
    private static Logger logger = Logger.getLogger(SampleMapper.class);

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();
    private static final String PROVIDER_ID = "keycloak-sample-mapper";

    public void postInit(KeycloakSessionFactory factory) {
        logger.error("Here I am ...");
    }

    @Override
    public String getDisplayCategory() {
        return "custom mapper";
    }

    @Override
    public String getDisplayType() {
        return "simple display";
    }

    @Override
    public String getHelpText() {
        return "Provides a simple example for scope mapping override.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public int getPriority() {
        return 99;
    }

    @Override
    public AccessToken transformAccessToken(AccessToken token, ProtocolMapperModel mappingModel,
                                            KeycloakSession session, UserSessionModel userSession,
                                            ClientSessionContext clientSessionCtx) {
        String[] arr = token.getScope().split(" ");
        Set<String> scopes = new HashSet<>();
        scopes.addAll(Arrays.asList(arr));

        if (scopes.contains("scope1")) {
            logger.error("Here I am ...");
            token.setScope("scope1");
        } else {
            logger.warn("scope1 is not currently supported by the client context.");
        }

        return token;
    }
}
