import '../style/pending.css';


export type PopupOption = {url: URL, title: string, w: number, h: number};

// https://stackoverflow.com/questions/4068373/center-a-popup-window-on-screen
export const popupCenter = ({url, title, w, h}: PopupOption) => {
    // Fixes dual-screen position                             Most browsers      Firefox
    const dualScreenLeft = window.screenLeft !==  undefined ? window.screenLeft : window.screenX;
    const dualScreenTop = window.screenTop !==  undefined   ? window.screenTop  : window.screenY;

    const width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    const height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

    const systemZoom = width / window.screen.availWidth;
    const left = (width - w) / 2 / systemZoom + dualScreenLeft;
    const top = (height - h) / 2 / systemZoom + dualScreenTop;
    const newWindow = window.open(url, title,
        `
      width=${w / systemZoom}, 
      height=${h / systemZoom}, 
      top=${top}, 
      left=${left}
      status=no, menubar=no, toolbar=no, resizable=no
      `
    ) as Window;
    newWindow.focus();
    return newWindow;
};


let pendingWorks = 0;
document.body.insertAdjacentHTML('beforeend',
    '<div id="Pending"><div class="loader">Loading...</div></div>');

export const asyncTaskWrapper = <A extends unknown[], R, P>(asyncFunc: (...args: A) => Promise<R>) => {
    return (...args: A) => new Promise<R>((resolve, reject) => {
        if (!pendingWorks)
            document.body.classList.add('pending');
        ++pendingWorks;
        asyncFunc(...args).then(resolve).catch(reject).finally(() => {
            --pendingWorks;
            if (!pendingWorks)
                document.body.classList.remove('pending');
        });
    });
};