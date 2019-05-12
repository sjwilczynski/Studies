<?php


namespace list7\command\pingPong;


class PingPongRegistry {

    /**
     * @var PingPongRegistry
     */
    private static $instance;

    /**
     * @var array
     */
    private $registry;

    /**
     * PingPongRegistry constructor.
     */
    public function __construct() {
        $this->registry = array();
    }

    public static function getInstance() {
        if (self::$instance == null) {
            self::$instance = new PingPongRegistry();
        }
        return self::$instance;
    }

    public function registerPingCommand() {
        array_push($this->registry, 'ping');
    }

    public function registerPongCommand() {
        array_push($this->registry, 'pong');
    }

    /**
     * @return array
     */
    public function getRegistry(): array {
        return $this->registry;
    }
}