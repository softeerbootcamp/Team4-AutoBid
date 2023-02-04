# 가동중인 autobid 도커 중단 및 삭제
sudo docker ps -a -q --filter "name=autobid" | grep -q . && docker stop autobid && docker rm autobid | true

# 기존 이미지 삭제
sudo docker rmi benny1020/autobid:1.0

# 도커 허브 이미지 pull
sudo docker pull benny1020/autobid:1.0

# docker run
docker run -d -p 8080:8080 -v /home/ec2-user:/config --name autobid benny1020/autobid:1.0

docker rmi -f $(docker images -f "dangling=true" -q) || true
