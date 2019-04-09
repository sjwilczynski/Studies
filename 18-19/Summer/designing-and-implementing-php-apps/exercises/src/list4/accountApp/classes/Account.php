<?php


namespace list4\accountApp\classes;


class Account {

    /**
     * @var int
     */
    private $id;
    /**
     * @var string
     */
    private $email;

    /**
     * @var string
     */
    private $description;

    /**
     * @var int
     */
    private $points;

    /**
     * @var bool
     */
    private $active;

    /**
     * Account constructor.
     * @param int $id
     * @param string $email
     * @param string $description
     * @param int $points
     * @param bool $active
     */
    public function __construct(int $id, string $email, string $description, int $points, bool $active) {
        $this->id = $id;
        $this->email = $email;
        $this->description = $description;
        $this->points = $points;
        $this->active = $active;
    }


    /**
     * @return int
     */
    public function getId(): int {
        return $this->id;
    }

    /**
     * @return string
     */
    public function getEmail(): string {
        return $this->email;
    }

    /**
     * @return string
     */
    public function getDescription(): string {
        return $this->description;
    }

    /**
     * @return int
     */
    public function getPoints(): int {
        return $this->points;
    }

    /**
     * @return bool
     */
    public function isActive(): bool {
        return $this->active;
    }

    /**
     * @param string $description
     */
    public function setDescription(string $description): void {
        $this->description = $description;
    }

    /**
     * @param int $points
     */
    public function setPoints(int $points): void {
        $this->points = $points;
    }

    /**
     * @param bool $active
     */
    public function setActive(bool $active): void {
        $this->active = $active;
    }

    public function setId(int $id) {
        $this->id = $id;
    }


    public static function toJson(Account $account): string {
        $data = array("id" => $account->getId(), "email" => $account->getEmail(), "description" => $account->getDescription(), "points" => $account->getPoints(), "active" => $account->isActive());
        return json_encode($data);
    }

    public static function fromJson(string $json): Account {
        $array = json_decode($json, true);
        return new Account($array["id"], $array["email"], $array["description"], $array["points"], $array["active"]);
    }

}