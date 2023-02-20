import {emojisplosions} from "emojisplosion";

const blast = emojisplosions.bind(null, {
    emojis: [
        "🚐", "🚘", "🚗", "🚙", "🛻", "😁", "🤣", "😃",
        "😆", "😍", "🤩", "😎", "🤖", "😻", "🙈", "🙉",
        "🙊", "💪", "👌", "👋", "🙌", "💝", "💖", "💗",
        "🧡", "💛", "💚", "💙", "💜", "🚀", "🔥", "✨",
        "🎉", "💯",
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
    interval: 1000
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