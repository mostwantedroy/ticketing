
![[erd.png]]

- 사용자는 특정 공연을 여러좌석을 선택해서 여러번 예매를 할 수 있습니다.
    - 하나의 예매는 여러 좌석을 포함할 수 있다. 하나의 좌석은 여러 예매(공연A, 공연B)에 포함될 수 있습니다. Reservation과 VenueSeat은 N 대 N 관계이기 때문에 ReservationSeat이라는 Entity를 뽑아냅니다.
- 초기 modeling 과정에서 reservationSeat에 seatType이 존재하지 않았고, reservation에 performance의 normalPrice, vipPrice column이 존재하지 않았습니다. 이 경우 관리자가 performance의 가격을 변경하거나 VenueSeat의 type을 수정할 경우, 이미 추가된 reservation에서 가격 정보를 계산할 경우 데이터 불일치 발생할 수 있을 것 같습니다. 데이터가 중복될 수 있지만 결국 reservation은 그 당시의 venueSeatType의 정보와 performance 가격 정보를 capture한 정보라고 봐야한다고 생각하여, 이들을 가지고 있어야 하고 그 공연의 정보나 좌석 정보를 수정하더라도 side effect이 사라지게 되므로 이들을 추가하도록 수정했습니다.
- performance의 capacity는 총 cap과 남은 cap으로 나눠서 관리합니다. 