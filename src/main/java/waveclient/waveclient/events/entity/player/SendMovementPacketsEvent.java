/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.events.entity.player;

public class SendMovementPacketsEvent {
    public static class Pre {
        private static final Pre INSTANCE = new Pre();

        public static Pre get() {
            return INSTANCE;
        }
    }

    public static class Post {
        private static final Post INSTANCE = new Post();

        public static Post get() {
            return INSTANCE;
        }
    }
}
