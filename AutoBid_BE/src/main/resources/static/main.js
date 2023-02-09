var btn = document.querySelector('#btn');

var stompClient = null;

function connect(event){
    var socket = new Socket("/auction-room");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected);
    event.preventDefault();
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/subscribe/auction'); // 구독을 퍼블리쉬로 보낸다.

    // Tell your username to the server
    stompClient.send("/publish/auction/createRoom",
        {},
        JSON.stringify({auctionId: "auctionRoomId", username: "user"})
    )
}

btn.addEventListener('submit', connect, true)


// var socket = new
// $(function () {
//     var sendBtn = $('.send');
//     var auctionRoomId = $('.content').data('auction-id');
//     var user = $('.content').data('user');
//
//     var sock = new SockJS("/auction-room");
//     var client = Stomp.over(sock); // 1. SockJS를 내부에 들고 있는 client를 내어준다.
//
//     // 2. connection이 맺어지면 실행된다.
//     client.connect({}, function () {
//         // 3. send(path, header, message)로 메시지를 보낼 수 있다.
//         client.send('/publish/createRoom', {}, JSON.stringify({auctionId: auctionRoomId, username: user}));
//         // 4. subscribe(path, callback)로 메시지를 받을 수 있다. callback 첫번째 파라미터의 body로 메시지의 내용이 들어온다.
//         client.subscribe('/subscribe/auction/room/' + auctionRoomId, function (auctionUserDto) {
//             var content = JSON.parse(auction.body);
//             console.log(content);
//         });
//     });
//
//     sendBtn.click(function () {
//         client.send('/publish/chat/message', {}, JSON.stringify({auctionId: auctionRoomId, username: user}));
//     });
// });

