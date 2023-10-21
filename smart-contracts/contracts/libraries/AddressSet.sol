// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.20;

struct AddressSet {
    address[] elements;
    mapping(address => uint256) indexes;
}

library AddressSetLib {
    function insert(
        AddressSet storage set,
        address add
    ) internal returns (bool) {
        if (contains(set, add)) {
            return false;
        }
        set.elements.push(add);
        set.indexes[add] = set.elements.length;
        return true;
    }

    function remove(
        AddressSet storage set,
        address add
    ) internal returns (bool) {
        if (!contains(set, add)) {
            return false;
        }
        uint256 index = set.indexes[add];
        uint256 lastIndex = set.elements.length;
        if (index != lastIndex) {
            address last = set.elements[lastIndex];
            set.elements[index - 1] = last;
            set.indexes[last] = index;
        }
        delete set.indexes[add];
        set.elements.pop();
        return true;
    }

    function sendEth(
        AddressSet storage set,
        uint256 value
    ) internal returns (bool) {
        for (uint256 index = 0; index < set.elements.length; index++) {
            address add = set.elements[index];
            (bool succeed, ) = add.call{value: value}("");
            if (!succeed) {
                return false;
            }
        }
        return true;
    }

    function contains(
        AddressSet storage set,
        address add
    ) internal view returns (bool) {
        return set.indexes[add] != 0;
    }

    function size(AddressSet storage set) internal view returns (uint256) {
        return set.elements.length;
    }
}
