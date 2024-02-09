#include "MicroBit.h"
#include "MicroBitUARTService.h"
#include "aes.hpp"

MicroBit uBit;

static PacketBuffer encrypt_ecb(ManagedString trame)
{
    uint8_t key[] = {0x60, 0x3d, 0xeb, 0x10, 0x15, 0xca, 0x71, 0xbe, 0x2b, 0x73, 0xae, 0xf0, 0x85, 0x7d, 0x77, 0x81,
                     0x1f, 0x35, 0x2c, 0x07, 0x3b, 0x61, 0x08, 0xd7, 0x2d, 0x98, 0x10, 0xa3, 0x09, 0x14, 0xdf, 0xf4 };

    uint8_t in[16];
    for (int i = 0; i < 16; ++i)
    {
        in[i] = (uint8_t)trame.charAt(i);
    }

    struct AES_ctx ctx;

    AES_init_ctx(&ctx, key);
    AES_ECB_encrypt(&ctx, in);

   return PacketBuffer(in, 16);
}

void sendData(ManagedString trame)
{
    ManagedString encryptedData = encrypt_ecb(trame);
    uBit.radio.datagram.send(encryptedData);
}

void receiveData()
{
    ManagedString delimiter = "END";
    ManagedString trame;
    trame = uBit.serial.readUntil(delimiter);
    if (trame.length()>0) {
        sendData(trame);
        //uBit.sleep(100);
        uBit.serial.send("Received");

    }
}

int main()
{
    uBit.init();
    uBit.serial.baud(115200);
    uBit.serial.setRxBufferSize(200);

    uBit.radio.enable();
    uBit.radio.setGroup(3);

    while (true) {
        receiveData();

    }

    release_fiber();
}