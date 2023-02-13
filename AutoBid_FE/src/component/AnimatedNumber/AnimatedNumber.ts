const counters = document.getElementsByClassName("animated-number");
const speed = 500;

export default function AnimatedNumber() {
    for (let c of counters) {
        let counter = c as HTMLElement;
        const animate = () => {
            const value = +counter.getAttribute('value')!;
            const data = +counter.innerText;

            const time = value / speed;
            if (data < value) {
                counter.innerText = Math.ceil(data + time) + '';
                setTimeout(animate, 1);
            } else {
                counter.innerText = value + '';
            }
        }
        animate();
    }
}