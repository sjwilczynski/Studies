<?php


namespace list7\router;


use list7\command\Command;

class DirectRouter implements Router {

    /**
     * @var array
     */
    private $handlers;

    /**
     * DirectRouter constructor.
     * @param array $handlers
     */
    public function __construct(array $handlers) {
        $this->handlers = $handlers;
    }

    /**
     * @throws NoRouteFoundException
     */
    public function route(Command $command) {
        if (!array_key_exists(get_class($command), $this->handlers)) {
            throw new NoRouteFoundException();
        } else {
            $handler = new $this->handlers[get_class($command)];
            $handler($command);
        }
    }
}