import Component from "../../core/component";
import "./animatedtext.css";

class AnimatedText extends Component<any, { text: string }> {
    mounted() {
        this.animate();
    }

    animate() {
        const { $target } = this;
        let text = this.props.text;

        timeoutRecursive();

        function timeoutRecursive() {
            setTimeout(() => {
                if ($target.isConnected && text.length) {
                    $target.innerText += text.substring(0, 1);
                    text = text.substring(1);
                    timeoutRecursive();
                }
            });
        }
    }
}

export default AnimatedText;