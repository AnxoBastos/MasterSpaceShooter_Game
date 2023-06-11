package org.daw2.anxobastosrey.masterspaceshooter.listeners;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;

public class MenuClickListener {
    public final MasterSpaceShooter game;
    public final Camera camera;
    public final Viewport viewport;

    public MenuClickListener(MasterSpaceShooter game, Camera camera, Viewport viewport) {
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;
    }
}
