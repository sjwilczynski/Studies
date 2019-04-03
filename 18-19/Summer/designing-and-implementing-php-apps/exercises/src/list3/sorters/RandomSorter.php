<?php


namespace list3\sorters;


class RandomSorter implements ListSorter {

    public function sort(array $list): array {
        $newList = $list;
        shuffle($newList);
        return $newList;
    }
}