import {emojisplosions} from "emojisplosion";

const blast = emojisplosions.bind(null, {
    emojis: [
        "๐", "๐", "๐", "๐", "๐ป", "๐", "๐คฃ", "๐",
        "๐", "๐", "๐คฉ", "๐", "๐ค", "๐ป", "๐", "๐",
        "๐", "๐ช", "๐", "๐", "๐", "๐", "๐", "๐",
        "๐งก", "๐", "๐", "๐", "๐", "๐", "๐ฅ", "โจ",
        "๐", "๐ฏ",
    ],
    physics: {
        gravity: 0.3,
        // @ts-ignore
        initialVelocities: {
            rotation: {
                max: 14,
                min: -14
            },
        },
        rotationDeceleration: 1,
    },
    emojiCount: 10,
    position: () => ({
        x: Math.random() * innerWidth,
        y: Math.random() * innerHeight,
    }),
    interval: 300
});

export const Emoji = {
    __cancel: () => {},
    blast() {
        this.__cancel();
        this.__cancel = blast().cancel;
    },
    cancel() {
        this.__cancel();
    }
}