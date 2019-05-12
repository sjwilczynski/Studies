<?php


namespace list7\command\pingPong;


class PongHandler {
    public function __invoke(PongCommand $command) {
        PingPongRegistry::getInstance()->registerPongCommand();
    }
}