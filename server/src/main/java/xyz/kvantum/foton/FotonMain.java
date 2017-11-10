package xyz.kvantum.foton;

import com.github.intellectualsites.kvantum.api.core.Kvantum;
import com.github.intellectualsites.kvantum.api.util.RequestManager;
import com.github.intellectualsites.kvantum.implementation.DefaultLogWrapper;
import com.github.intellectualsites.kvantum.implementation.ServerContext;
import com.github.intellectualsites.kvantum.implementation.error.KvantumException;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.util.Optional;

public class FotonMain
{

    private static final String HOME_KEY = "FOTON_HOME";

    public static void main(final String[] args) throws Exception
    {
        String homePath = null;

        if ( !System.getenv().containsKey( HOME_KEY ) )
        {
            System.out.printf( "FATAL: Environment variable [%s] not set!\n", HOME_KEY );
            System.exit( -1 );
        } else
        {
            homePath = System.getenv( HOME_KEY );
            if ( SystemUtils.IS_OS_UNIX && ( !homePath.startsWith( "/" ) ) )
            {
                homePath = "/" + homePath;
            }
        }

        final Optional<Kvantum> serverOptional = ServerContext.builder()
                .coreFolder( new File( homePath ) )
                .logWrapper( new DefaultLogWrapper() )
                .router( RequestManager.builder().build() )
                .standalone( true )
                .build()
                .create();

        if ( !serverOptional.isPresent() )
        {
            throw new KvantumException( "Failed to initialize the server..." );
        }

        final Kvantum server = serverOptional.get();
        server.start();
    }

}
