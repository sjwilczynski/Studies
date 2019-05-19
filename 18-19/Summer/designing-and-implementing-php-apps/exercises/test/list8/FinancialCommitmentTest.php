<?php


namespace list8;


use list8\financialCommitment\events\FinancialCommitmentCanceled;
use list8\financialCommitment\events\FinancialCommitmentOverPaid;
use list8\financialCommitment\events\FinancialCommitmentPaid;
use list8\financialCommitment\events\FinancialCommitmentPartiallyPaid;
use list8\financialCommitment\FinancialCommitment;
use list8\financialCommitment\FinancialCommitmentIllegalOperationException;
use Money\Money;
use PHPUnit\Framework\TestCase;
use Ramsey\Uuid\Uuid;

class FinancialCommitmentTest extends TestCase {

    /**
     * @var FinancialCommitment
     */
    private $commitment;


    protected function setUp(): void {
        $this->commitment = new FinancialCommitment(Money::PLN(500), Uuid::uuid1());
    }

    /**
     * @throws FinancialCommitmentIllegalOperationException
     */
    public function testPayCommitmentPartially() {
        $this->commitment->registerPayment(Money::PLN(300));
        $events = $this->commitment->getEvents();
        $lastEmittedEvent = end($events);

        self::assertTrue(is_a($lastEmittedEvent, FinancialCommitmentPartiallyPaid::class));
        self::assertEquals(Money::PLN(200), $this->commitment->getBalance());
    }

    /**
     * @throws FinancialCommitmentIllegalOperationException
     */
    public function testPayCommitment() {
        $this->commitment->registerPayment(Money::PLN(500));
        $events = $this->commitment->getEvents();
        $lastEmittedEvent = end($events);

        self::assertTrue(is_a($lastEmittedEvent, FinancialCommitmentPaid::class));
        self::assertTrue($this->commitment->isPaid());
    }

    /**
     * @throws FinancialCommitmentIllegalOperationException
     */
    public function testCancelCommitment() {
        $this->commitment->cancel();
        $events = $this->commitment->getEvents();
        $lastEmittedEvent = end($events);

        self::assertTrue(is_a($lastEmittedEvent, FinancialCommitmentCanceled::class));
        self::assertTrue($this->commitment->isCanceled());
    }

    /**
     * @throws FinancialCommitmentIllegalOperationException
     */
    public function testOverpayCommitment() {
        $this->commitment->registerPayment(Money::PLN(600));
        $events = $this->commitment->getEvents();
        $lastEmittedEvent = end($events);

        self::assertTrue(is_a($lastEmittedEvent, FinancialCommitmentOverPaid::class));
        self::assertTrue($this->commitment->isOverPaid());

    }

    /**
     * @throws FinancialCommitmentIllegalOperationException
     */
    public function testPayCanceledCommitment() {
        self::expectException(FinancialCommitmentIllegalOperationException::class);

        $this->commitment->cancel();
        $this->commitment->registerPayment(Money::PLN(100));
    }

    /**
     * @throws FinancialCommitmentIllegalOperationException
     */
    public function testPayPaidCommitment() {
        self::expectException(FinancialCommitmentIllegalOperationException::class);

        $this->commitment->registerPayment(Money::PLN(500));
        $this->commitment->registerPayment(Money::PLN(100));
    }

    /**
     * @throws FinancialCommitmentIllegalOperationException
     */
    public function testCancelPaidCommitment() {
        self::expectException(FinancialCommitmentIllegalOperationException::class);

        $this->commitment->registerPayment(Money::PLN(500));
        $this->commitment->cancel();
    }

    /**
     * @throws FinancialCommitmentIllegalOperationException
     */
    public function testCancelCanceledCommitment() {
        self::expectException(FinancialCommitmentIllegalOperationException::class);

        $this->commitment->cancel();
        $this->commitment->cancel();
    }

}