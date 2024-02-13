public class SoundState implements PhoneState
{
    @Override
    public void onReceiveMessage(String message, Phone phone)
    {
        phone.beepBeep();
        System.out.println(message);
    }

    @Override
    public void onReceiveCall(Phone phone)
    {
        phone.playRingTone();
    }

    @Override
    public void onVolumeButtonUp(Phone phone)
    {
        int volume = phone.getVolume();
        if (volume < 100)
        {
            phone.turnVolumeUp();
        }
    }

    @Override
    public void onVolumeButtonDown(Phone phone)
    {
        int vol = phone.getVolume();
        if (vol > 1)
        {
            phone.turnVolumeDown();
        }
        else
        {
            phone.changeToSilentState();
        }
    }
}