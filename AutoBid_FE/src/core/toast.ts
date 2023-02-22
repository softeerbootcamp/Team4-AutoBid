const Toast = {
    show(msg: string, timeMilli: number) {
        const $toast = this.__createToastElement(msg);
        setTimeout(this.__setFadeOut.bind(this, $toast), timeMilli + 500);
        setTimeout(this.__removeToastElement.bind(this, $toast), timeMilli + 1500);
    },

    __createToastElement(msg: string) {
        const $toast = document.createElement('toast');
        $toast.innerText = msg;
        this.__setFadeIn($toast);
        document.body.appendChild($toast);
        return $toast;
    },

    __removeToastElement($toast: HTMLElement) {
        $toast.remove();
    },

    __setFadeIn($toast: HTMLElement) {
        $toast.style.opacity = '1';
        $toast.animate([{ opacity: 0 }, { opacity: 1 }], 500);
    },

    __setFadeOut($toast: HTMLElement) {
        $toast.style.opacity = '0';
        $toast.animate([{ opacity: 1 }, { opacity: 0 }], 500);
    }
}

export default Toast;