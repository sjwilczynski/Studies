<?php


namespace list7\command;

use list7\router\NoRouteFoundException;
use list7\router\Router;


/* obczaj symphony messanger */
class CommandBus {

    /**
     * @var Router
     */
    private $router;

    /**
     * CommandBus constructor.
     * @param Router $router
     */
    public function __construct(Router $router) {
        $this->router = $router;
    }

    /**
     * @throws NoRouteFoundException
     */
    public function dispatch(Command $command) {
        $this->router->route($command);
    }

}