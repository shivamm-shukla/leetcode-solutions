class Solution {
    public boolean lemonadeChange(int[] bills) {
        int rs5 = 0;
        int rs10 = 0;
        int rs20 = 0;

        for (int bill : bills){
            switch(bill){
                case 5:
                    rs5++;
                    break;

                case 10: 
                    rs10++;
                    rs5--;
                    if (rs5 < 0) return false;
                    break;
                
                case 20:
                    rs20++;
                    if (rs10 > 0){
                        rs10--;
                        rs5--;
                    }
                    else {
                        rs5 -= 3;
                    }

                    if (rs5 < 0) return false;
            }
        }
        return true;
    }
}