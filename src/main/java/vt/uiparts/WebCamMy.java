package vt.uiparts;

import com.vaadin.ui.ComponentContainer;
import org.vaadin.teemu.webcam.Webcam;
import vt.main.UImy;
//import org.vaadin.teemu.webcam.client.WebcamServerRpc;

/**
 * Created by Андрей on 26.04.2015.
 */
public class WebCamMy {
    private Webcam webcam;

    public void initWebCam(ComponentContainer parentLayout, final UImy uimy){
        webcam =  new Webcam();
//        webcam.

        parentLayout.addComponent(webcam);
    }
}
