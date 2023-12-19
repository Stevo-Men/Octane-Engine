package theprojekt;

import javax.sound.sampled.*;

public enum SoundEffect {

    MURLOC("audios/murloc.wav", 100),
    DETECTED_FX("audios/detected_FX.wav", 100),
    DRONE_AMBIENT("audios/droneAmbient.wav", 100),
    KNIFE_THROW("audios/throwingKnifeFx.wav", 100),
    GAMEOVER_FX("audios/gameOverFX.wav", 100),
    DASH_FX("audios/dashFx.wav", 100),
    NPC_SCREAMING("audios/npcScreaming.wav", 100);

    private int maxCooldown;
    private int cooldown;
    private String path;
    private boolean isDetectingSoundPlaying = false;
    private AudioInputStream stream;

    SoundEffect(String path , int maxCooldown) {
        this.maxCooldown = maxCooldown;
        this.path = path;

    }



    public void play() {
        if (!isDetectingSoundPlaying) {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream stream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream(path));
                clip.open(stream);

                clip.start();

                isDetectingSoundPlaying = true;


                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        isDetectingSoundPlaying = false;
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}