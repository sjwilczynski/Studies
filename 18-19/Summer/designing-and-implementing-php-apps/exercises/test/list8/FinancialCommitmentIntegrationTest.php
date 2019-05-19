<?php


namespace list8;


use list7\command\CommandBus;
use list7\command\IllegalCommandException;
use list7\router\DirectRouter;
use list7\router\NoRouteFoundException;
use list8\financialCommitment\commands\CancelFinancialCommitmentCommand;
use list8\financialCommitment\commands\CancelFinancialCommitmentHandler;
use list8\financialCommitment\commands\CreateFinancialCommitmentCommand;
use list8\financialCommitment\commands\CreateFinancialCommitmentHandler;
use list8\financialCommitment\commands\PayFinancialCommitmentCommand;
use list8\financialCommitment\commands\PayFinancialCommitmentHandler;
use list8\financialCommitment\repository\FinancialCommitmentNotFoundException;
use list8\financialCommitment\repository\FinancialCommitmentRepository;
use list8\financialCommitment\repository\InMemoryRepository;
use Money\Money;
use PHPUnit\Framework\TestCase;
use Ramsey\Uuid\Uuid;
use Ramsey\Uuid\UuidInterface;

class FinancialCommitmentIntegrationTest extends TestCase {

    /**
     * @var UuidInterface
     */
    private $commitmentId;

    /**
     * @var FinancialCommitmentRepository
     */
    private $repository;

    /**
     * @var CommandBus
     */
    private $commandBus;

    protected function setUp(): void {
        $this->commitmentId = Uuid::uuid1();
        $this->repository = InMemoryRepository::getInstance();

        $router = new DirectRouter([
            CreateFinancialCommitmentCommand::class => CreateFinancialCommitmentHandler::class,
            CancelFinancialCommitmentCommand::class => CancelFinancialCommitmentHandler::class,
            PayFinancialCommitmentCommand::class => PayFinancialCommitmentHandler::class
        ]);

        $this->commandBus = new CommandBus($router);
    }

    /**
     * @throws NoRouteFoundException
     * @throws FinancialCommitmentNotFoundException
     * @throws IllegalCommandException
     */
    public function testCommitmentCorrectPaymentFlow() {
        $this->commandBus->dispatch(new CreateFinancialCommitmentCommand($this->commitmentId, Money::PLN(500)));

        $this->commandBus->dispatch(new PayFinancialCommitmentCommand($this->commitmentId, Money::PLN(250)));
        $this->commandBus->dispatch(new PayFinancialCommitmentCommand($this->commitmentId, Money::PLN(250)));
        $commitment = $this->repository->load($this->commitmentId);

        self::assertTrue($commitment->isPaid());
    }

    /**
     * @throws NoRouteFoundException
     * @throws FinancialCommitmentNotFoundException
     * @throws IllegalCommandException
     */
    public function testCommitmentPaidPartiallyAndCanceled() {
        $this->commandBus->dispatch(new CreateFinancialCommitmentCommand($this->commitmentId, Money::PLN(500)));

        $this->commandBus->dispatch(new PayFinancialCommitmentCommand($this->commitmentId, Money::PLN(250)));
        $this->commandBus->dispatch(new CancelFinancialCommitmentCommand($this->commitmentId));
        $commitment = $this->repository->load($this->commitmentId);

        self::assertTrue($commitment->isCanceled());
    }

    /**
     * @throws NoRouteFoundException
     * @throws IllegalCommandException
     */
    public function testCommitmentIllegalOperationPerformed() {
        $this->expectException(IllegalCommandException::class);
        $this->commandBus->dispatch(new CreateFinancialCommitmentCommand($this->commitmentId, Money::PLN(500)));

        $this->commandBus->dispatch(new PayFinancialCommitmentCommand($this->commitmentId, Money::PLN(550)));
        $this->commandBus->dispatch(new PayFinancialCommitmentCommand($this->commitmentId, Money::PLN(300)));
    }

}