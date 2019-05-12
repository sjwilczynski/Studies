<?php


namespace list7\router;


use list7\command\Command;

interface Router {

    /**
     * @throws NoRouteFoundException
     */
    public function route(Command $command);

}