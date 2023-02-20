import Component from "../../core/component";
import {emojisplosions} from "emojisplosion";
import {EmojiState, emojiStateSelector} from "../../store/emoji";

const {cancel} = emojisplosions({
    emojis: ["🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻",
        "🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻",
        "🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻",
        "🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻", "🚐", "🚘", "🚗", "🚙", "🛻",
        "😁", "🤣", "😃", "😆", "😍", "🤩", "😎", "🤖", "😻", "🙈", "🙉", "🙊", "💪", "👌", "👋", "🙌",
        "💝", "💖", "💗", "🧡", "💛", "💚", "💙", "💜", "🚀", "🔥", "✨", "🎉", "💯",],
    physics: {
        gravity: 0.13,
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
    interval: 200
    // , uniqueness: 1
});

class EmojiExplosion extends Component<EmojiState> {
    stateSelector(globalState: any): EmojiState | undefined {
        return globalState[emojiStateSelector];
    }

    template() {
        const {isEmojiOn} = this.state!;
        return `
            <div class="emoji-explosion">
                ${isEmojiOn ? setTimeout(cancel, 5000): ''}
            </div>
        `
    }

    onStateChanged(prevState: EmojiState) {
        this.render();
    }
}

export default EmojiExplosion;