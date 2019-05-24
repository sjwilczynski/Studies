<?php


namespace list8\financialCommitment;


use list8\event\Event;
use list8\financialCommitment\events\FinancialCommitmentCanceled;
use list8\financialCommitment\events\FinancialCommitmentCreated;
use list8\financialCommitment\events\FinancialCommitmentOverPaid;
use list8\financialCommitment\events\FinancialCommitmentPaid;
use list8\financialCommitment\events\FinancialCommitmentPartiallyPaid;
use Money\Money;
use Ramsey\Uuid\UuidInterface;

class FinancialCommitment {
    /**
     * @var UuidInterface
     */
    private $id;


    /**
     * @var Event[]
     */
    private $events = [];

    /**
     * @var Money
     */
    private $balance;


    // gdzie powinno być generowane id dla tworzonych obiektów?? ale chyba jest potrzebne po stronie klienta jeśli używamy jakiejś asynchronicznej kolejki - pytanie z intro07-08
    public function __construct(Money $amount, UuidInterface $id) {
        $this->balance = $amount;
        $this->id = $id;
        $this->events[] = new FinancialCommitmentCreated($amount);
        // jednak lepiej jak event ma id commitmentu, bo wtedy mozna latwo szukac po bazie eventow
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

    // pytanie z 08:
    // jakbysmy sobie mysleli ze mamy w bazie same eventy to wtedy chcemy oddtwarzac stan z tych eventow (EventSourcing) - wtedy mozna to zrobic tak jak ponizej
    // wersjonowanie po to zeby uniknac problemow jak 2 ludzi chce na raz zrobic przeciwstawne operacje (unique na id, typie eventu, wersji)
    // wydajnosciowo naprawiamy to tak ze co n eventow robmy snapshoty (bo eventy sa append only) i jak pobieramy z repozytorium to aplikujemy eventy tylko od pewnej wersji
    //problem -  aco jak chcemy zmienic klasy eventw - np wchodzi rodo a uzytkownik chce byc zapomniany - Versioning in Event sourcing systems (ksiazka)

//    protected function record(Event $event)
//    {
//        $event->setVersion(++$this->version);
//
//        $this->events[] = $event;
//
//        $this->apply($event);
//    }
//
//    protected function apply(Event $event)
//    {
//        switch (get_class($event)) {
//            case FinancialCommitmentCanceled::class:
//                $this->status = 'cancelled';
//                $this->balance = $this->balance->subtract($this->balance);
//
//                break;
//        }
//    }
//
//    public static function fromEvents(array $events)
//    {
//        $o = new self();
//
//        foreach ($events as $event) {
//            $o->apply($event);
//            $o->version = $event->getVersion();
//        }
//
//        return $o;
//    }


    /**
     * @throws FinancialCommitmentIllegalOperationException
     */
    public function cancel(): void {

        if ($this->isDone()) {
            throw new FinancialCommitmentIllegalOperationException();
        }

        $this->events[] = new FinancialCommitmentCanceled();
        //$this->record(new FinancialCommitmentCanceled()); - do EventSoucingu
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