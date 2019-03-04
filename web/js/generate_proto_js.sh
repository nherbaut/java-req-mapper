protoc -I=../../protos requirements.proto  --js_out=import_style=commonjs:./ --grpc-web_out=import_style=commonjs,mode=grpcwebtext:./
npm install
npx webpack -d client.js
cp ./dist/main.js ../src/main/resources/static/js/
