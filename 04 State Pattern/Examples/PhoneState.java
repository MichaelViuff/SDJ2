public interface PhoneState
{
    void onReceiveMessage(String message, Phone phone);
    void onReceiveCall(Phone phone);
    void onVolumeButtonUp(Phone phone);
    void onVolumeButtonDown(Phone phone);
}