/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.waypoints.events;

import waveclient.waveclient.systems.waypoints.Waypoint;

public record WaypointRemovedEvent(Waypoint waypoint) {
}
