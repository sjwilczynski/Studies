<?php


namespace list8\financialCommitment;


use list8\event\Event;
use list8\financialCommitment\events\FinancialCommitmentCanceled;
use list8\financialCommitment\events\FinancialCommitmentCreated;
use list8\financialCommitment\events\FinancialCommitmentOverPaid;
use list8\financialCommitment\events\FinancialCommitmentPaid;
use list8\financialCommitment\events\FinancialCommitmentPartiallyPaid;
use Money\Money;
use Ramsey\Uuid\Uuid;
use Ramsey\Uuid\UuidInterface;

class FinancialCommitment {
    /**
     * @var UuidInterface
     */
    private $id;

    /**
     * @var UuidInterface
     */
    private $userId;

    /**
     * @var Event[]
     */
    private $events = [];

    /**
     * @var Money
     */
    private $balance;

    public function __construct(Money $amount, $userId) {
        $this->id = Uuid::uuid1();
        $this->balance = $amount;
        $this->userId = $userId;
        $this->events[] = new FinancialCommitmentCreated($amount);
    }

    /**
     * @throws FinancialCommitmentIllegalOperationException
     */
    public function registerPayment(Money $payment): void {

        if ($this->isDone()) {
            throw new FinancialCommitmentIllegalOperationException();
        }

        $this->balance = $this->balance->subtract($payment);
        $this->events[] = new FinancialCommitmentPartiallyPaid($payment);

        if ($this->balance->isZero()) {
            $this->events[] = new FinancialCommitmentPaid($payment);
        }

        if ($this->balance->isNegative()) {
            $this->events[] = new FinancialCommitmentOverPaid($payment);
        }
    }

    /**
     * @throws FinancialCommitmentIllegalOperationException
     */
    public function cancel(): void {

        if ($this->isDone()) {
            throw new FinancialCommitmentIllegalOperationException();
        }

        $this->events[] = new FinancialCommitmentCanceled();
    }

    public function getBalance(): Money {
        return $this->balance;
    }

    public function getEvents(): array {
        return $this->events;
    }

    public function getId(): UuidInterface {
        return $this->id;
    }

    public function isPaid(): bool {
        return $this->balance->isZero();
    }

    public function isCanceled(): bool {
        return is_a(end($this->events), FinancialCommitmentCanceled::class);
    }

    public function isOverPaid(): bool {
        return $this->balance->isNegative();
    }

    private function isDone(): bool {
        return $this->isOverPaid() || $this->isCanceled() || $this->isPaid();
    }
}