package com.yx.play.db

import androidx.room.Database
import androidx.room.RoomDatabase


/**
 * @description
 * @version
 */
@Database(
    entities =
    [
//        BucketInfoEntity::class,
//        UserEntity::class,
//        CommonDataEntity::class,
//        GroupMemberEntity::class,
//        ConversationEntity::class,
//        ConversationSnapShotEntity::class,
//        FileDownloadEntity::class,
//        FileTransferEntity::class,
//        UserPrivacySettingEntity::class,
//        GroupSequenceEntity::class,
//        FriendApplyEntity::class,
//        QrCodeEntity::class,
//        UserPhoneContactEntity::class,
//        UserThirdPartAccountEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDataBase : RoomDatabase() {

//    abstract fun commonDataDao(): CommonDataDao
//    abstract fun bucketDao(): BucketDao
//    abstract fun userDao(): UserDao
//    abstract fun groupMemberDao(): GroupMemberDao
//    abstract fun conversationDao(): ConversationDao
//    abstract fun fileTransferDao(): FileTransferDao
//    abstract fun fileDownloadDao(): FileDownloadDao
//    abstract fun userPrivacySettingDao(): UserPrivacySettingDb.UserPrivacySettingDbDao
//    abstract fun groupSequenceDao(): GroupSequenceDao
//    abstract fun friendApplyDao(): FriendApplyDao
//    abstract fun friendShipDao(): RelationShipDao
//    abstract fun qrCodeDao(): QrCodeDao
//    abstract fun atSearchDao(): AtSearchDao
//    abstract fun selectSearchDao(): SelectSearchDao
//    abstract fun globalSearchDao(): GlobalSearchDao
//    abstract fun userPhoneContactDao(): UserPhoneContactDb.UserPhoneContactDao
//
//
//    abstract fun userThirdPartAccountDao(): UserThirdPartAccountDao

}