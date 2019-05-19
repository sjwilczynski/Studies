<?php


namespace list8\event;


class EventBus {

    /**
     * @var EventBus
     */
    private static $instance;

    private function __construct() {
    }

    public static function getInstance() {
        if (self::$instance == null) {
            self::$instance = new EventBus();
        }
        return self::$instance;
    }

    public function dispatch(Event $event) {
        // sending to handlers and then:
        // storing event, sending notification, what can be done here?
    }
}