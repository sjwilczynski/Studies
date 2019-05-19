<?php


namespace list7\router;


use Exception;
use list7\command\Command;
use list7\command\IllegalCommandException;

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
     * @throws IllegalCommandException
     */
    public function route(Command $command) {
        if (!array_key_exists(get_class($command), $this->handlers)) {
            throw new NoRouteFoundException();
        } else {
            try {
                $handler = new $this->handlers[get_class($command)];
                $handler($command);
            } catch (Exception $exception) {
                throw new IllegalCommandException($exception);
            }

        }
    }
}