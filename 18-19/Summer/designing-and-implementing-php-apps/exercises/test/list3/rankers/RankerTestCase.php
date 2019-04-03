<?php


namespace list3\rankers;


use DateTimeImmutable;
use list3\Application;
use list3\Student;
use PHPUnit\Framework\TestCase;

abstract class RankerTestCase extends TestCase {
    protected $students;
    protected $applications;

    protected function setUp(): void {
        $this->students = $this->initializeStudents();
        $this->applications = $this->initializeApplications($this->students);
    }


    public function initializeStudents(): array {
        $s1 = new Student("Joe", "Doe");
        $s2 = new Student("John", "Dow");
        $s3 = new Student("Doe", "Joe");
        $s4 = new Student("Johnny", "Donny");
        $s5 = new Student("Johen", "Dohen");
        $s6 = new Student("Eoj", "Eod");
        return array($s1, $s2, $s3, $s4, $s5, $s6);

    }

    public function initializeApplications(array $students): array {
        $a1 = new Application($students[0], new DateTimeImmutable("2019-03-16"), 30);
        $a2 = new Application($students[1], new DateTimeImmutable("2019-03-15"), 40);
        $a3 = new Application($students[2], new DateTimeImmutable("2019-03-17"), 50);
        $a4 = new Application($students[3], new DateTimeImmutable("2019-03-18"), 20);
        $a5 = new Application($students[4], new DateTimeImmutable("2019-03-14"), 80);
        $a6 = new Application($students[5], new DateTimeImmutable("2019-03-20"), 45);

        return array($a1, $a2, $a3, $a4, $a5, $a6);
    }
}