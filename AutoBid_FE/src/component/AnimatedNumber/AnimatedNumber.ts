const counters = document.getElementsByClassName("animated-number");
const speed = 2000;

export default async function AnimatedNumber() {
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
        await animate();
    }
}