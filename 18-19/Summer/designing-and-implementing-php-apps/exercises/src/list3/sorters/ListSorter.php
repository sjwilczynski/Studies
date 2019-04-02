<?php


namespace list3\sorters;


interface ListSorter {
    public function sort(array $list) : array;
}