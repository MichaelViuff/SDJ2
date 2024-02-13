public class Phone
{

    private PhoneState currentState;
    private PhoneState silentState, vibrateState, soundState;
    private int currentVolume;

    public Phone()
    {
        silentState = new SilentState();
        vibrateState = new VibrateState();
        soundState = new SoundState();
        currentState = soundState;
    }

    public void changeToSilentState()
    {
        currentState = silentState;
    }

    public void changeToVibrateState()
    {
        currentState = vibrateState;
    }

    public void changeToSoundState()
    {
        currentState = soundState;
    }

    public void receiveMessage(String message)
    {
        currentState.onReceiveMessage(message, this);
    }

    public void receiveCall()
    {
        currentState.onReceiveCall(this);
    }

    public void volumeUpButton()
    {
        currentState.onVolumeButtonUp(this);
    }

    public void volumeDownButton()
    {
        currentState.onVolumeButtonDown(this);
    }
    
    void turnVolumeUp()
    {
        currentVolume++;
    }

    public void turnVolumeDown()
    {
        currentVolume--;
    }

    public int getVolume()
    {
        return currentVolume;
    }

    public void playRingTone()
    {
        System.out.println("Ring Ring, Ring Ring");
    }

    public void vibrate()
    {
        System.out.println("Brrrrr!");
    }

    public void beepBeep()
    {
        System.out.println("Beep Beep!");
    }
}
