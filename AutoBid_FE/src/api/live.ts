import dotenv from "dotenv";
import {CompatClient, Stomp} from "@stomp/stompjs";
import SockJS from "sockjs-client";
dotenv.config();

export type LiveUser = {
    userId: number, username: string, phoneNumber: string, price: number
}

export type LiveDTO = {
    price: number, users: LiveUser[], numberOfUsers: number
};

let stompClient: CompatClient|null = null;

let onStart: (live: LiveDTO) => any = () => {};
let onEnd: (live: LiveDTO) => any = () => {};
let onBid: (live: LiveDTO) => any = () => {};

export const setOnStart = (handler: (live: LiveDTO) => any) => onStart = handler;
export const setOnEnd = (handler: (live: LiveDTO) => any) => onEnd = handler;
export const setOnBid = (handler: (live: LiveDTO) => any) => onBid = handler;

const API_BASE_URL = process.env.API_BASE_URL || 'https://www.autobid.site';
const LIVE_ENDPOINT = process.env.LIVE_ENDPOINT || '/auction-room';
const ENTER_ROUTE = process.env.ENTER_ROUTE || '/enter';
const START_ROUTE = process.env.START_ROUTE || '/start';
const END_ROUTE = process.env.END_ROUTE || '/end';
const BID_ROUTE = process.env.BID_ROUTE || '/bid';

export const requestSocketSession = (auctionId: number, test = false) => {
    if (test) return;
    const socket = SockJS(`${API_BASE_URL}${LIVE_ENDPOINT}`);
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        // STOMP over Websocket 이 성공적으로 연결되었을 때

        // Sub /start/{auctionId}
        // 서버 측에서 Pub /start/{auctionId}로 했을 때 받는 곳
        stompClient?.subscribe(`${START_ROUTE}/${auctionId}`, ({ body }) => {

            // 경매가 시작한 상태임을 안내받는 핸들러
            // 이 부분이 동작 하는 상황
            //  1. 사용자가 시작 전인 방에서 대기하고 있다가 서버 측에서 경매가 시작되었을 때 (broadcast, 참고 API : convertAndSend)
            //  2. 사용자가 이미 시작된 방에 접속했을 때 (uni-cast, 참고 API : convertAndSendToUser)

            const live = JSON.parse(body) as LiveDTO;
            onStart(live);
            // onStart 가 할 일
            //  1. 경매 '전' View 를 경매 '중' 으로 업데이트 하고 현재가 및 입찰 호가 갱신
            //  2. 입찰에 성공한(했었던) 5명의 사용자에 대해 순위 정보 갱신

        });

        // Sub /end/{auctionId}
        // 서버 측에서 Pub /end/{auctionId}로 했을 때 받는 곳
        stompClient?.subscribe(`${END_ROUTE}/${auctionId}`, ({ body }) => {

            // 경매가 종료되었음을 안내받는 핸들러
            // 이 부분이 동작 하는 상황
            //  1. 클라이언트 로컬 시간이 endTime 을 초과하여 입찰 버튼이 비활성화 된 상태로 서버의 최종 판결을 대기하는 상황에서
            //  2. 서버 측에서 endTime 을 초과한 경매장에 대해 배치처리를 완료했을 때 (broadcast, 참고 API : convertAndSend)
            //  3. TODO(논의 해 볼 사항) 또는 이미 종료된 방에 접속 시도를 했을 때
            const live = JSON.parse(body) as LiveDTO;
            onEnd(live);
            // onEnd 기 할 일
            //  1. 만약 '상황 1.'이 아닌 경우 입찰 버튼을 비활성화 하고 경매 종료 View 로 갱신
            //  2. 만약 live.users[0].userId 가 currentLogOn.userId 와 같은 경우 🎉
            //  3. disconnectSocketSession() 호출하여 열려있는 STOMP 세션 종료
        });

        // Sub /bid/{auctionId}
        // 서버 측에서 Pub /bid/{auctionId}로 했을 때 받는 곳
        stompClient?.subscribe(`${BID_ROUTE}/${auctionId}`, ({ body }) => {

            // 새로운 입찰이 성공했음을 안내받는 핸들러ㅓ
            // 이 부분이 동작 하는 상황
            //  1. 클라이언트는 이전 상태의 현재가를 View 를 통해 보여주고 있는 상황에서
            //  2. 같은 방의 자신 또는 누군가가 새로운 가격으로 입찰에 성공했고
            //  3. 서버 측에서 성공된 입찰 정보를 redis 에 반영했을 때 (broadcast, 참고 API : convertAndSend)
            const live = JSON.parse(body) as LiveDTO;
            onBid(live);
            // onBid 가 할 일
            //  1. 현재가와 입찰 호가를 live.price, live.price + (입찰단위) 로 갱신
            //  2. 입찰에 성공한(했었던) 5명의 사용자에 대해 순위 정보 갱신

        });

        // Pub /enter/{auctionId}
        // 서버 측에서 Sub /enter/{auctionId}로 했을 때 받는 곳 (@MessageMapping("/enter/{auctionId}"))
        // 이 Pub 의 역할 (상기 Subscriber controller 가 수행 할 일)
        //  1. 서버 측에서 경매가 아직 시작되지 않은 경우, 클라이언트가 대기하기 위해
        //  2. 서버 측에서 경매가 진행 중인 경우, 클라이언트에게 /start/${auctionId} 를 Pub 하기 위해
        //  3. 서버 측에서 경매가 이미 종료된 경우, 클라이언트에게 /end/${auctionId} 를 Pub 하기 위해
        stompClient?.publish({ destination: `${ENTER_ROUTE}/${auctionId}`, body: 'enter' });
    });
}

export const disconnectSocketSession = (test = false) => {
    if (test) return;
    if (stompClient) {
        stompClient.disconnect();
    }
    stompClient = null;
}

declare global {
    interface Window { __LIVE_TEST__: any }
}
window.__LIVE_TEST__ = {
    start(live: LiveDTO) {
        onStart(live);
    },
    bid(live: LiveDTO) {
        onBid(live);
    },
    end(live: LiveDTO) {
        onEnd(live);
    }
}