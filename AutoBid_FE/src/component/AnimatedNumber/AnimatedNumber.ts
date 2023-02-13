import Component from "../../core/component";
import "./animatednumber.css";

class AnimatedNumber extends Component<any, { start: number, destination: number, speed: number }> {
    template(): InnerHTML["innerHTML"] {
        const { start } = this.props;
        return start.toLocaleString();
    }

    mounted() {
        this.animate();
    }

    animate() {
        const $target = this.$target;
        const { destination, speed } = this.props;
        const delta = destination / speed;
        let value = this.props.start;
        timeoutRecursive();

        function timeoutRecursive() {
            setTimeout(() => {
                if (!$target.isConnected) return;
                if (value === destination) return;

                if (value < destination) {
                    value += delta;
                } else {
                    value -= delta;
                }
                value = Math.ceil(value);

                $target.innerHTML = value.toLocaleString();
                timeoutRecursive();
            });
        }
    }
}

export default AnimatedNumber;