<?php

namespace list7\command;

use list7\command\pingPong\PingCommand;
use list7\command\pingPong\PingHandler;
use list7\command\pingPong\PingPongRegistry;
use list7\command\pingPong\PongCommand;
use list7\command\pingPong\PongHandler;
use list7\router\DirectRouter;
use list7\router\NoRouteFoundException;
use PHPUnit\Framework\TestCase;

class CommandBusTest extends TestCase {

    /**
     * @var CommandBus
     */
    private $commandBus;

    protected function setUp(): void {
        $router = new DirectRouter([
            PingCommand::class => PingHandler::class,
            PongCommand::class => PongHandler::class,
        ]);

        $this->commandBus = new CommandBus($router);
    }

    /**
     * @throws NoRouteFoundException
     * @throws IllegalCommandException
     */
    public function testPingPongDispatch() {
        $this->commandBus->dispatch(new PingCommand());
        $this->commandBus->dispatch(new PongCommand());
        $this->commandBus->dispatch(new PongCommand());
        $this->commandBus->dispatch(new PingCommand());

        $registry = PingPongRegistry::getInstance()->getRegistry();

        self::assertEquals(array('ping', 'pong', 'pong', 'ping'), $registry);
    }

    /**
     * @throws NoRouteFoundException
     * @throws IllegalCommandException
     */
    public function testNoHandlerDispatch() {
        self::expectException(NoRouteFoundException::class);
        $this->commandBus->dispatch(new FakeCommand());
    }


}
