#!/bin/bash

### with docker

runApp:docker () {
  buildNode:docker
  run:back:docker
}

run:back:docker () {
  docker-compose up gradle
}

buildNode:docker() {
  case `uname -s` in
    MINGW*)
      docker-compose run --rm node sh -c "npm install --no-bin-link" && gulpBuild:docker
      ;;
    *)
      docker-compose run --rm node sh -c "npm install" && gulpBuild:docker
  esac
}

gulpBuild:docker() {
  docker-compose run --rm node sh -c "node_modules/gulp/bin/gulp.js build"
}

### without docker

runApp () {
  buildNode
  run:back
}

run:back () {
  gradle clean run
}

buildNode() {
  case `uname -s` in
    MINGW*)
      npm install --no-bin-link && gulpBuild
      ;;
    *)
      npm install && gulpBuild
  esac
}

gulpBuild() {
  npm run build;
}

for param in "$@"
do
  case $param in
    runApp:docker)
      runApp:docker
      ;;
    run:back:docker)
      run:back:docker
      ;;
    buildNode:docker)
      buildNode:docker
      ;;
    gulpBuild:docker)
      gulpBuild:docker
      ;;
    runApp)
      runApp
      ;;
    run:back)
      run:back
      ;;
    buildNode)
      buildNode
      ;;
    gulpBuild)
      gulpBuild
      ;;
    *)
      echo "Invalid argument : $param"
  esac
  if [ ! $? -eq 0 ]; then
    exit 1
  fi
done
