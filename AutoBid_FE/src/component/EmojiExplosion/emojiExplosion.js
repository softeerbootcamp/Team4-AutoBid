import { emojisplosion } from "emojisplosion";

export function emojiExplosion() {
    emojisplosion({
        emojis: ["🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻",
            "🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻",
            "😁", "😂", "🤣", "😃", "😅", "😆", "😍", "🤩", "😎", "🤖", "😻", "🙈", "🙉", "🙊", "💪", "👌", "👋", "🙌",
            "💝", "💖", "💗", "🧡", "💛", "💚", "💙", "💜", "🚀", "⛄", "🔥", "✨", "🎉", "💯",],
        physics: {
            initialVelocities: {
                rotation: {
                    max: 14,
                    min: -14
                },
                x: 7,
                y: -14
            },
            rotationDeceleration: 1.01
        }
    });
}
