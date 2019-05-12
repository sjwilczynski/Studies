<?php


namespace list7\command\pingPong;


class PingHandler {
    public function __invoke(PingCommand $command) {
        PingPongRegistry::getInstance()->registerPingCommand();
    }
}