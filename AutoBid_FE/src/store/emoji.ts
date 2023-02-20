import {Reducer} from "redux";
import {registerReducer} from "../core/store";

export enum Emoji {
    ON = 'emoji/ON',
    OFF = 'emoji/OFF'
}

export type EmojiState = { isEmojiOn: Emoji };

const initialState: EmojiState = {isEmojiOn: Emoji.OFF};

const emoji: Reducer<EmojiState> = (state = initialState, action) => {
    switch (action.type) {
        case Emoji.ON:
            return {isEmojiOn: Emoji.ON};
        case Emoji.OFF:
            return {isEmojiOn: Emoji.OFF};
        default:
            return {isEmojiOn: Emoji.OFF};
    }
}

export const emojiStateSelector = registerReducer(emoji);