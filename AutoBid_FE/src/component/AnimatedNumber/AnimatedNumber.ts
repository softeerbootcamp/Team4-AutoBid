import Component from "../../core/component";
import "./animatednumber.css";

class AnimatedNumber extends Component<any, { start: number, destination: number, speed: number }> {
    template(): InnerHTML["innerHTML"] {
        const {start} = this.props;
        return start.toLocaleString();
    }

    mounted() {
        this.animate();
    }

    animate() {
        const $target = this.$target;
        const {start, destination, speed} = this.props;
        const delta = Math.abs(start - destination) / speed;
        let value = start;
        timeoutRecursive();

        function timeoutRecursive() {
            setTimeout(() => {
                if (!$target.isConnected) return;
                if (value === destination) return;
                if (Math.abs(value - destination) < delta) {
                    value = destination;
                } else if (value < destination) {
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