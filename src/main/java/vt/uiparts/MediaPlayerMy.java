package vt.uiparts;

import com.kbdunn.vaadin.addons.mediaelement.MediaComponent;
import com.kbdunn.vaadin.addons.mediaelement.PlaybackEndedListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.teemu.webcam.Webcam;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Андрей on 26.04.2015.
 */
public class MediaPlayerMy {

    private MediaComponent mediaComponent;

    MediaComponent videoPlayer_Local;
    MediaComponent videoPlayer_Inet;
    MediaComponent audioPlayer;

    public void initMediaPlayer(VerticalLayout parentLayout){
        // Audio player with PlaybackEndedListener
        audioPlayer = new MediaComponent(MediaComponent.Type.AUDIO);
        parentLayout.addComponent(audioPlayer);
        audioPlayer.setSource(new FileResource(new File("/path/to/song.mp3")));
        audioPlayer.addPlaybackEndedListener(new PlaybackEndedListener() {

            @Override
            public void playbackEnded(MediaComponent component) {
                component.setSource(new FileResource(new File("/path/to/next/song.m4a")));
                component.play();
            }
        });
        audioPlayer.play();

        // Video player
        videoPlayer_Local = new MediaComponent(MediaComponent.Type.VIDEO);
        parentLayout.addComponent(videoPlayer_Local);
//        videoPlayer_Local.setSource(new FileResource(new File("/path/to/video.mp4")));

        // YouTube player
        videoPlayer_Inet = new MediaComponent(MediaComponent.Type.VIDEO);
        parentLayout.addComponent(videoPlayer_Inet);
        videoPlayer_Inet.setSource(new ExternalResource("https://youtu.be/kh29_SERH0Y"));
    }


    public void update(String pathname) {
        if (pathname == null || pathname.length() == 0) {
            clearPreview();
            return;
        }
        // Выделим расширение файла
        File file = new File(pathname);
        int lastIndexOf = pathname.lastIndexOf(".");
        String extension = (lastIndexOf == -1) ? "" : pathname.substring(lastIndexOf);
        // Ограничение на размер файла для предпросмотра
        final int PREVIEW_FILE_LIMIT = 20 * 100 * 1024 * 1024;
        // Расширения файлов для предпросмотра с помощью объекта Embedded (изображения, Flash и так далее)
        final String[] musicExtensions = new String[]{
                ".mp3"
        };
        final String[] videoExtensions = new String[]{
                ".avi", ".mp4", ".wmv"
        };

        // Скроем объект, используемый для предпросмотра
        audioPlayer.setVisible(false);
        videoPlayer_Local.setVisible(false);

        videoPlayer_Inet.setVisible(true); // TODO: it can be update in future

        // Проверим, не превышает ли размер файла пороговый
        if (file.length() > PREVIEW_FILE_LIMIT) {
            clearPreview();
            return;
        }
        // Если расширение файла — в списке
        if (Arrays.asList(musicExtensions).contains(extension)) {
            audioPlayer.setVisible(true);
            audioPlayer.setSource(new FileResource(new File(pathname)));
            audioPlayer.play();
        }

        // Если расширение файла — в списке
        if (Arrays.asList(videoExtensions).contains(extension)) {
            videoPlayer_Local.setVisible(true);
            videoPlayer_Local.setSource(new FileResource(new File(pathname)));
            videoPlayer_Local.play();
        }
    }

    // Скрыть предварительный просмотр файла
    private void clearPreview() {
        videoPlayer_Local.setSource(null);
        audioPlayer.setSource(null);
        //audioPlayer.setVisible(true);
    }

}
