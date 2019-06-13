<?php

namespace list11;

use PHPUnit\Framework\TestCase;
use Symfony\Component\Console\Application;
use Symfony\Component\Console\Tester\CommandTester;

class NumbersCommandTest extends TestCase {

    /**
     * @var string
     */
    private $commandName;
    /**
     * @var CommandTester
     */
    private $commandTester;

    protected function setUp(): void {
        $application = new Application();
        $application->add(new NumbersCommand());

        $command = $application->find(NumbersCommand::getDefaultName());
        $this->commandName = $command->getName();
        $this->commandTester = new CommandTester($command);
    }

    public function testConvertNumberInHex() {

        $this->commandTester->execute(array(
            'command' => $this->commandName,
            'number' => '2a',
            'base' => '16'
        ));

        $this->assertStringContainsString('XLII', $this->commandTester->getDisplay());
    }

    public function testNumberExceedingRange() {

        $this->commandTester->execute(array(
            'command' => $this->commandName,
            'number' => '-5',
            'base' => '10'
        ));

        $this->assertStringContainsString('Number exceeded range allowed for roman numbers', $this->commandTester->getDisplay());
    }

}
