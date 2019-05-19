<?php


namespace list7\router;


use list7\command\Command;
use list7\command\IllegalCommandException;

interface Router {

    /**
     * @throws NoRouteFoundException
     * @throws IllegalCommandException
     */
    public function route(Command $command);

}