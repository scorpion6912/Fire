#include "MicroBit.h"
#include "MicroBitUARTService.h"
#include "aes.hpp"

MicroBit uBit;

static int decrypt_ecb(PacketBuffer buf)
{
    uint8_t key[] = {0x60, 0x3d, 0xeb, 0x10, 0x15, 0xca, 0x71, 0xbe, 0x2b, 0x73, 0xae, 0xf0, 0x85, 0x7d, 0x77, 0x81,
                     0x1f, 0x35, 0x2c, 0x07, 0x3b, 0x61, 0x08, 0xd7, 0x2d, 0x98, 0x10, 0xa3, 0x09, 0x14, 0xdf, 0xf4 };

    uint8_t in[16];
    for (int i = 0; i < 16; ++i)
    {
        in[i] = buf[i];
    }

    struct AES_ctx ctx;

    AES_init_ctx(&ctx, key);
    AES_ECB_decrypt(&ctx, in);

    for (int i = 0; i < 16; ++i)
    {
        buf[i] = (char)in[i];
    }

    return (0);
}

void onData(MicroBitEvent)
{
    PacketBuffer encryptedData = uBit.radio.datagram.recv();

    decrypt_ecb(encryptedData);
    bool present = false;
    ManagedString key("]");

    char buf[16];
    for (int i = 0; i < 16; ++i)
    {
        buf[i] = (char)encryptedData[i];
    }

    for (int i = 0; i < 16; ++i){
        uBit.serial.send(buf[i]);
        if (ManagedString(buf[i]) == key) {
            present = true;
        }
    }
    if(present) {
        uBit.serial.send("\n");
    }
}


int main() {
    uBit.init();
    uBit.serial.baud(115200);

    uBit.messageBus.listen(MICROBIT_ID_RADIO, MICROBIT_RADIO_EVT_DATAGRAM, onData);
    uBit.radio.enable();
    uBit.radio.setGroup(3);

    while (1)
        uBit.sleep(1000);

    release_fiber();
}