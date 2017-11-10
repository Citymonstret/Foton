package xyz.kvantum.foton;

import com.github.intellectualsites.kvantum.api.core.Kvantum;
import com.github.intellectualsites.kvantum.api.util.RequestManager;
import com.github.intellectualsites.kvantum.implementation.DefaultLogWrapper;
import com.github.intellectualsites.kvantum.implementation.ServerContext;
import com.github.intellectualsites.kvantum.implementation.error.KvantumException;

import java.io.File;
import java.util.Optional;

public class FotonMain
{

    public static void main(final String[] args) throws Exception
    {
        final Optional<Kvantum> serverOptional = ServerContext.builder()
                .coreFolder( new File( "./server/web/" ) )
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

        server.getRouter().clear();


        server.start();
    }

}
